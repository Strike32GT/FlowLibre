from rest_framework import status

from rest_framework.decorators import api_view, permission_classes

from rest_framework.permissions import AllowAny, IsAuthenticated

from rest_framework.response import Response

from rest_framework_simplejwt.tokens import RefreshToken

from .serializers import UserSerializer, UserRegistrationSerializer, LoginSerializer, LibrarySongSerializer, PlaylistSerializer, PlaylistSongSerializer

from .models import User, LibrarySong, Playlist, PlaylistSong
from songs.serializers import SongSerializer



# Create your views here.

@api_view(['POST'])

@permission_classes([AllowAny])

def register(request):

    serializer = UserRegistrationSerializer(data=request.data)

    if serializer.is_valid():

        user = serializer.save()

        return Response({

            'user': UserSerializer(user).data,

            'message': 'Usuario creado exitosamente'

        }, status=status.HTTP_201_CREATED)

    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)





@api_view(['POST'])

@permission_classes([AllowAny])

def login(request):

    serializer = LoginSerializer(data=request.data)

    if serializer.is_valid():

        user = serializer.validated_data['user']

        refresh = RefreshToken.for_user(user)

        return Response({

            'user': UserSerializer(user).data,

            'refresh': str(refresh),

            'access': str(refresh.access_token),

        })

    return Response(serializer.errors, status=status.HTTP_400_BAD_REQUEST)







@api_view(['GET'])

@permission_classes([IsAuthenticated])

def profile(request):

    serializer = UserSerializer(request.user)

    return Response(serializer.data)    







@api_view(['POST'])

@permission_classes([IsAuthenticated])

def add_to_library(request):

    serializer = LibrarySongSerializer(data=request.data)

    if serializer.is_valid():

        serializer.save(user=request.user)

        return Response({"message":"Cancion agregada a tu biblioteca"}, status=201)

    return Response(serializer.errors, status=400)



@api_view(['GET'])
@permission_classes([IsAuthenticated])
def get_library_songs(request):
    library_songs = LibrarySong.objects.filter(user=request.user)
    songs = [ls.song for ls in library_songs]
    serializer = SongSerializer(songs, many=True)
    return Response(serializer.data)




@api_view(['POST'])
@permission_classes([IsAuthenticated])
def create_playlist(request):
    serializer = PlaylistSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save(user=request.user)
        return Response(serializer.data, status=201)
    return Response(serializer.errors, status=400)




@api_view(['GET'])
@permission_classes([IsAuthenticated])
def get_user_playlists(request):
    playlists = Playlist.objects.filter(user=request.user)
    serializer = PlaylistSerializer(playlists, many=True)
    return Response(serializer.data)




@api_view(['POST'])
@permission_classes([IsAuthenticated])
def add_song_to_playlist(request):
    serializer = PlaylistSongSerializer(data=request.data)
    if serializer.is_valid():
        serializer.save()
        return Response({"message": "Canción agregada a playlist"}, status=201)
    return Response(serializer.errors, status=400)



@api_view(['GET'])
@permission_classes([IsAuthenticated])
def get_playlist_songs(request, playlist_id):
    playlist_songs = PlaylistSong.objects.filter(playlist_id=playlist_id, playlist__user=request.user)
    songs = [ps.song for ps in playlist_songs]
    serializer = SongSerializer(songs, many=True)
    return Response(serializer.data)