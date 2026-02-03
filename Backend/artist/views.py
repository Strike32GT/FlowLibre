from rest_framework.views import APIView
from rest_framework.response import Response
from django.db.models import Q
from .models import Artist
from .serializers import ArtistSerializer
from songs.serializers import SongSerializer
from albums.serializers import AlbumSerializer



# Create your views here.
class ArtistSearchView(APIView):
    def get(self, request):
        query = request.GET.get('q', '').strip()
        
        if not query:
            artists = Artist.objects.all()[:10]
        else:
            artists = Artist.objects.filter(
                Q(name__icontains=query)
            )
        
        serializer = ArtistSerializer(artists, many=True)
        return Response(serializer.data)




class ArtistDetailView(APIView):
    def get(self, request, artist_id):
        try:
            artist = Artist.objects.get(id=artist_id)
            songs = Song.objects.filter(artist_id=artist_id)
            albums = Album.objects.filter(artist_id=artist_id)

            return Response({
                'artist': ArtistSerializer(artist).data,
                'songs': SongSerializer(songs, many=True).data,
                'albums': AlbumSerializer(albums, many=True).data
            })

        except Artist.DoesNotExist:
            return Response({'error': 'Artist not found'}, status=404)