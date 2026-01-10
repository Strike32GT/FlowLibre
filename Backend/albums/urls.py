from django.urls import path
from .views import AlbumListView, AlbumDetailView

urlpatterns = [
    path('',AlbumListView.as_view()),
    path('<int:album_id>/', AlbumDetailView.as_view()),
]