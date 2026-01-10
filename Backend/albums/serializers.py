from rest_framework import serializers
from .models import Album

class AlbumSerializer(serializers.ModelSerializer):
    class Meta:
        model = Album
        fields = [
            'id',
            'title',
            'cover_url',
            'release_date',
            'artist_id',
            'created_at'
        ]