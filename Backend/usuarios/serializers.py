from rest_framework import serializers
from .models import User
import hashlib

class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = [
            'id',
            'username',
            'email',
            'role',
            'created_at'
        ]

class UserCreateSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)

    class Meta:
        model = User
        fields = ['username', 'email', 'password', 'role']

    def create(self, validated_data):
        

        password = validated_data.pop('password')
        password_hash = hashlib.sha256(password.encode()).hexdigest()

        user = User.objects.create(
            password_hash=password_hash,
            **validated_data
        )
        return user
