<template>
    <v-container class="d-flex justify-center align-center" style="height: 100vh;">
      <v-card class="pa-6" elevation="2" max-width="500">
        <v-card-title class="justify-center">Success!</v-card-title>
        <v-card-text>Your wishlist has been submitted. Thank you!</v-card-text>
        <v-card-actions class="justify-end">
          <v-btn @click="$router.push({ name: 'SubmitWishlist' })" color="primary">
            Submit Another Wishlist
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </template>
  
  <script>
  export default {
    name: "SuccessPage",
  };
  </script>
  