from django.db import models

class Song(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=150)
    duration = models.IntegerField(null=True, blank=True)  
    audio_url=models.TextField(null=True, blank=True)
    cover_url=models.TextField()
    artist_id = models.IntegerField(null=True, blank=True)
    album_id= models.IntegerField(null=True, blank=True)
    created_at = models.DateTimeField()

    class Meta:
        db_table = 'songs'
        managed = False

    def __str__(self):
        return self.title    
