from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status
from .models import Album
from .serializers import AlbumSerializer

# Create your views here.
class AlbumListView(APIView):
    def get(self, request):
        albums = Album.objects.all().order_by('-created_at')
        serializer = AlbumSerializer(albums, many=True)
        return Response(serializer.data)
    


class AlbumDetailView(APIView):
    def get(self, request, album_id):
        try:
            album = Album.objects.get(id=album_id)
            serializer=AlbumSerializer(album)
            return Response(serializer.data)
        
        except Album.DoesNotExist:
            return Response(
                {"error":"Album no he encontrado."},
                status=status.HTTP_404_NOT_FOUNDs
            )