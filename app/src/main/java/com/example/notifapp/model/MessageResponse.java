package com.example.notifapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


    public class MessageResponse {
        @SerializedName("status")
        @Expose
        private String status;

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("nachrichten")
        @Expose
        private ArrayList<MessageModel> nachrichten;

        // Getters and Setters

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ArrayList<MessageModel> getNachrichten() {
            return nachrichten;
        }

        public void setNachrichten(ArrayList<MessageModel> nachrichten) {
            this.nachrichten = nachrichten;
        }
    }

