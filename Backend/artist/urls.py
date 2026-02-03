from django.urls import path
from .views import ArtistSearchView
 
urlpatterns = [
    path('search/', ArtistSearchView.as_view()),
    path('<int:artist_id>/', ArtistDetailView.as_view()),
]