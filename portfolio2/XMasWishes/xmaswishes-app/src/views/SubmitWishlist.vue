<template>
  <v-container class="d-flex justify-center align-center" style="height: 100vh;">
    <v-card class="pa-6" elevation="2" max-width="500">
      <v-card-title class="justify-center">Submit Your Wishlist</v-card-title>
      <v-card-text>
        <v-text-field
          v-model="name"
          label="Your Name"
          outlined
          dense
          required
        ></v-text-field>

        <div v-for="(wish, index) in wishes" :key="index" class="mb-3">
          <v-text-field
            v-model="wishes[index]"
            label="Wish"
            outlined
            dense
            clearable
          ></v-text-field>
        </div>

        <v-btn
          variant="outlined"
          color="primary"
          class="mt-3"
          block
          @click="addWish"
        >
          + Add Wish
        </v-btn>
      </v-card-text>

      <v-card-actions class="d-flex justify-end">
        <v-btn
          color="primary"
          @click="submitWishlist"
          :disabled="!name || wishes.length === 0"
        >
          Submit
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "SubmitWishlist",
  data() {
    return {
      name: "",
      wishes: [""],
    };
  },
  methods: {
    addWish() {
      this.wishes.push("");
    },
    async submitWishlist() {
      try {
        const payload = {
          name: this.name,
          wishes: this.wishes.filter((wish) => wish.trim() !== ""),
        };

        await axios.post("http://localhost:3000/api/submitWishlist", payload);

        // Reset form and navigate to success page
        this.name = "";
        this.wishes = [""];
        this.$router.push({ name: "Success" });
      } catch (error) {
        console.error("Failed to submit wishlist:", error);
        alert("An error occurred while submitting your wishlist.");
      }
    },
  },
};
</script>

<style scoped>
/* Custom styling to center the card */
</style>
