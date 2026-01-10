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
            'album_id',
            'created_at'
        ]