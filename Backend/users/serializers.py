from rest_framework import serializers
from django.contrib.auth import authenticate
from .models import User


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ['id', 'username', 'email', 'created_at']
        extra_kwargs = { 'password': {'write_only': True}}


class UserRegistrationSerializer(serializers.ModelSerializer):
    password = serializers.CharField(write_only=True)
    class Meta:
        model = User
        fields = ['username','email','password']


    def create(self, validated_data):
        password = validated_data.pop('password')
        user = User(
            username = validated_data['username'],
            email=validated_data['email']
        )
        user.set_password(password)
        user.save()
        return user




class LoginSerializer(serializers.ModelSerializer):
    email = serializers.EmailField()
    password = serializers.CharField()

    def validate(self, data):
        email = data.get('email')
        password = data.get('password')

        if email and password:
            try:
                user = User.objects.get(email=email)
                if user.check_password(password):
                    data['user']= user
                else:
                    raise serializers.ValidationError("Credenciales inválidas")

            except User.DoesNotExist:
                raise serializers.ValidationError("Credenciales inválidas")
        else:
            raise serializers.ValidationError("Se requieren email y password")

        return data    