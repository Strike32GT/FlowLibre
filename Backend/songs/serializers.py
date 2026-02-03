from rest_framework import serializers

from .models import Song



class SongSerializer(serializers.ModelSerializer):

    class Meta:

        model= Song

        fields = [

            'id',

            'title',

            'duration',

            'audio_url',

            'cover_url',

            'artist_id',

            'artist_name',

            'album_id',

            'created_at'

        ]







    def get_artist_name(self, obj):

        try:

            from artist.models import Artist

            artist = Artist.objects.get(id=obj.artist_id)

            return artist.name

        except Artist.DoesNotExist:

            return f"Artist {obj.artist_id}"

        except:

            return f"Artist {obj.artist_id}"