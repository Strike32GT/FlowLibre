from django.db import models


class Album(models.Model):
    id = models.AutoField(primary_key=True)
    title = models.CharField(max_length=150)
    cover_url=models.TextField(null=True, blank=True)
    release_date = models.DateField(null=True, blank=True)
    artist_id = models.IntegerField(null=True, blank=True)
    created_at = models.DateTimeField()

    class Meta:
        db_table = 'albums'
        managed = False

    def __str__(self):
        return self.title   