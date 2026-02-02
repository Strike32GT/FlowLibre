from django.urls import path
from .views import ArtistSearchView
 
urlpatterns = [
    path('search/', ArtistSearchView.as_view()),
]