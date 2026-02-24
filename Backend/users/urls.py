from django.urls import path
from . import views

urlpatterns = [
    path('register/', views.register, name='register'),
    path('login/', views.login, name='login'),
    path('profile/', views.profile, name='profile'),
    path('add-to-library', views.add_to_library, name='add-to-library'),
    path('library/', views.get_library_songs, name='library_songs'),
    path('playlists/', views.create_playlist, name='create_playlist'),
    path('playlists/my/', views.get_user_playlists, name='user_playlists'),
    path('playlists/add-song/', views.add_song_to_playlist, name='add_song_to_playlist'),
    path('playlists/<int:playlist_id>/songs/', views.get_playlist_songs, name='playlist_songs'),
    path('playlists/<int:playlist_id>/songs/<int:song_id>/exists/', views.check_song_in_playlist, name='check_song_in_playlist')
]
