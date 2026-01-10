from django.urls import path
from .views import SongListView, SongDetailView, SongByAlbumView

urlpatterns = [
    path('',SongListView.as_view()),
    path('<int:song_id>/',SongDetailView.as_view()),
    path('album/<int:album_id>/',SongByAlbumView.as_view()),
]