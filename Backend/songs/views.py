from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Song
from .serializers import SongSerializer

# Create your views here.

class SongListView(APIView):
    def get(self, request):
        songs = Song.objects.all().order_by('-created_at')
        serializer = SongSerializer(songs, many=True)
        return Response(serializer.data)

class SongDetailView(APIView):
    def get(self, request, song_id):
        try:
            song = Song.objects.get(id=song_id)
            serializer = SongSerializer(song)
            return Response(serializer.data)
        except Song.DoesNotExist:
            return Response(
                {"error":"Cancion no encontrada"},
                status=status.HTTP_404_NOT_FOUND
            )
        
class SongByAlbumView(APIView):
    def get(self,request,album_id):
        songs = Song.objects.filter(album_id=album_id)   
        serializer = SongSerializer(songs, many=True)
        return Response(serializer.data)     