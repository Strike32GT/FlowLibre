from rest_framework import serializers



from django.contrib.auth import authenticate



from .models import User, LibrarySong, Playlist, PlaylistSong











class UserSerializer(serializers.ModelSerializer):



    class Meta:



        model = User



        fields = ['id', 'username', 'email', 'created_at']



        extra_kwargs = { 'password': {'write_only': True}}











class UserRegistrationSerializer(serializers.ModelSerializer):

    password = serializers.CharField(write_only=True)



    class Meta:



        model = User

        fields = ['username','email','password']











    def create(self, validated_data):

        password = validated_data.pop('password')

        user = User(

            username = validated_data['username'],

            email=validated_data['email']

        )



        user.set_password(password)

        user.save()

        return user



















class LoginSerializer(serializers.ModelSerializer):



    email = serializers.EmailField()



    password = serializers.CharField()



    class Meta:

        model = User

        fields = ['email','password']



    def validate(self, data):



        email = data.get('email')



        password = data.get('password')







        if email and password:



            try:



                user = User.objects.get(email=email)



                if user.check_password(password):



                    data['user']= user



                else:



                    raise serializers.ValidationError("Credenciales inválidas")







            except User.DoesNotExist:



                raise serializers.ValidationError("Credenciales inválidas")



        else:



            raise serializers.ValidationError("Se requieren email y password")







        return data    

    









#Biblioteca - Canciones - Por usuario

class LibrarySongSerializer(serializers.ModelSerializer):

    song_title = serializers.CharField(source='song.title',read_only=True)

    song_artist = serializers.CharField(source='song.artist.name', read_only=True)

    song_duration = serializers.IntegerField(source='song.duration', read_only=True)

    song_audio_url = serializers.URLField(source='song.audio_file', read_only=True)

    song_cover_url = serializers.URLField(source='song.cover_image', read_only=True)





    class Meta:

        model = LibrarySong

        fields = ['id', 'song', 'song_title', 'song_artist', 'song_duration', 'song_audio_url', 'song_cover_url', 'created_at']

        read_only_fields = ['id', 'created_at']



class PlaylistSerializer(serializers.ModelSerializer):
    class Meta:
        model = Playlist
        fields = ['id', 'name', 'created_at']
        read_only_fields = ['id', 'created_at']




class PlaylistSongSerializer(serializers.ModelSerializer):
    song_title = serializers.CharField(source='song.title', read_only=True)
    song_artist = serializers.CharField(source='song.artist_name', read_only=True)
    
    class Meta:
        model = PlaylistSong
        fields = ['id', 'playlist', 'song', 'song_title', 'song_artist', 'added_at']
        read_only_fields = ['id', 'added_at']