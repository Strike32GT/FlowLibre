from django.urls import path
from .views import LoginView, UserDetailView, RegisterView

urlpatterns = [
    path('login/', LoginView.as_view()),
    path('<int:user_id>/', UserDetailView.as_view()),
    path('register/', RegisterView.as_view()),
]
