package com.example.demo.model;

public enum RolNombre {
    ROLE_ADMIN {
        @Override
        public String toString() {
            return "Administrador";
        }
    },
    ROLE_USER {
        @Override
        public String toString() {
            return "Usuario";
        }
    };
}