from django.db import models

class User(models.Model):
    id = models.AutoField(primary_key=True)
    username = models.CharField(max_length=50, unique=True)
    email = models.CharField(max_length=120, unique= True)
    password_hash = models.TextField()
    role = models.CharField(max_length=20, null=True, blank=True)
    created_at = models.DateTimeField()

    class Meta:
        db_table = 'users'
        managed = False   

    def __str__(self):
        return self.username