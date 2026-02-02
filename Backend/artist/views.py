from rest_framework.views import APIView
from rest_framework.response import Response
from django.db.models import Q
from .models import Artist
from .serializers import ArtistSerializer

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