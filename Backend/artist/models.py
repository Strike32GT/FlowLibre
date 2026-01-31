from django.db import models

# Create your models here.
class Artist(models.Model):
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=150)
    image_url = models.TextField(null=True, blank=True)
    created_at = models.DateTimeField(auto_now_add=True)


    class Meta:
        db_table = 'artists'
        managed = False



    def __str__(self):
        return self.name    
