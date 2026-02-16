from django.db import models
import hashlib 
# Create your models here.
class User(models.Model):
    username = models.CharField(max_length=50, unique=True)
    email = models.EmailField(unique=True)
    password_hash = models.CharField(max_length=255)
    role = models.CharField(max_length=20, default='USER')
    created_at = models.DateTimeField(auto_now_add=True)
    
    class Meta:
        db_table = 'users'


    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['username']
        
    def set_password(self, password):
        self.password_hash = hashlib.sha256(password.encode()).hexdigest()


    def check_password(self, password):
        return self.password_hash == hashlib.sha256(password.encode()).hexdigest()

    def __str__(self):
        return self.username
    


    def get_full_name(self):
        return self.username
    

    def get_short_name(self):
        return self.username
    

    def has_perm(self, perm, obj=None):
        if self.role == 'ADMIN':
            return True
        
        elif self.role == 'USER':
            if 'admin' in perm.lower() or 'delete' in perm.lower() or 'change' in perm.lower():
                return False
            return True
        return False


    def has_module_perms(self, app_label):
        if self.role == 'ADMIN':
            return True
        elif self.role == 'USER':
            if 'admin' in app_label.lower():
                return False
            return True
        return False
    

    @property
    def is_staff(self):
        return self.role == 'ADMIN'
    

    @property
    def is_superuser(self):
        return self.role == 'ADMIN'
    

    @property
    def is_active(self):
        return True
    

    @property
    def is_authenticated(self):
        return True
    

    @property
    def is_anonymous(self):
        return False
    
    

class LibrarySong(models.Model):
    user = models.ForeignKey(User, on_delete=models.CASCADE)
    song = models.ForeignKey('songs.Song', on_delete=models.CASCADE)
    created_at = models.DateTimeField(auto_now_add= True)

    class Meta:
        db_table = 'library_songs'
        unique_together = ['user', 'song']
            