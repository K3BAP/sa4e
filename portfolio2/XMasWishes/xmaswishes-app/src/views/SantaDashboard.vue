<template>
  <v-container class="d-flex justify-center align-center" style="height: 100vh">
    <v-card class="p-4" max-width="600">
      <v-card-title class="text-h5">Santa Dashboard</v-card-title>
      <v-card-text>
        <!-- Input for Santa to search a wishlist -->
        <v-text-field
          v-model="name"
          label="Enter a name"
          outlined
          class="mb-3"
        />
        <v-btn color="primary" @click="fetchWishlist" :loading="loading">
          Get Wishlist
        </v-btn>

        <!-- Display wishes -->
        <v-alert
          v-if="wishlist.length === 0 && !loading && searched"
          type="warning"
          class="mt-3"
        >
          No wishlist found for "{{ name }}".
        </v-alert>

        <v-list v-if="wishlist.length > 0" class="mt-3">
          <v-subheader>Wishes for {{ name }}</v-subheader>
          <v-list-item
            v-for="wish in wishlist"
            :key="wish.wish_id"
            class="d-flex align-center"
          >
            <v-container>
              <v-row>
                <v-col cols=2>
                  <v-checkbox
                    v-model="wish.completed"
                    :disabled="wish.completed"
                    @change="markWishAsCompleted(wish)"
                  />
                </v-col>
                <v-col cols="auto">
                  <v-list-item-content>
                    <v-list-item-title>{{ wish.wish }}</v-list-item-title>
                    <v-list-item-subtitle>Status: {{ wish.status }}</v-list-item-subtitle>
                  </v-list-item-content>
                </v-col>
              </v-row>
            </v-container>
          </v-list-item>
        </v-list>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import { ref } from "vue";
import axios from "axios";

export default {
  name: "SantaDashboard",
  setup() {
    const name = ref("");
    const wishlist = ref([]);
    const loading = ref(false);
    const searched = ref(false);

    // Fetch wishlist by name
    const fetchWishlist = async () => {
      if (!name.value) return;
      try {
        loading.value = true;
        const response = await axios.get(
          `http://localhost:3000/api/getWishlistByName?name=${encodeURIComponent(
            name.value
          )}`
        );
        const wishes = response.data.wishlist.wishes;
        wishlist.value = wishes.map((wish) => ({
          ...wish,
          completed: wish.status === "Zugestellt",
        }));
        searched.value = true;
      } catch (error) {
        console.error("Error fetching wishlist:", error);
        wishlist.value = [];
      } finally {
        loading.value = false;
      }
    };

    // Mark a wish as completed
    const markWishAsCompleted = async (wish) => {
      try {
        await axios.patch(`http://localhost:3000/api/setWishDelivered/${encodeURIComponent(
          wish.wish_id
        )}`);
        wish.completed = true; // Update the local state to mark as completed
        wish.status = "completed"; // Update status in the UI
      } catch (error) {
        console.error("Error marking wish as completed:", error);
        alert("Wish could not be marked as delivered!")
      }
    };

    return {
      name,
      wishlist,
      loading,
      searched,
      fetchWishlist,
      markWishAsCompleted,
    };
  },
};
</script>

<style scoped>
/* Add any specific styles here */
</style>
