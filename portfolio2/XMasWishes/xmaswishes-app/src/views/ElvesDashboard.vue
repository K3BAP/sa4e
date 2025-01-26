<template>
  <v-container class="d-flex justify-center align-center" style="height: 100vh">
    <v-card class="p-4" max-width="500">
      <v-card-title class="text-h5">Elves Dashboard</v-card-title>
      <v-card-text>
        <v-alert
          v-if="loading"
          type="info"
          class="mb-3"
        >
          Fetching a wish for you...
        </v-alert>

        <div v-if="currentWish">
          <p><strong>Wish:</strong> {{ currentWish.wish.wish }}</p>
          <v-btn
            color="success"
            @click="markAsCompleted"
          >
            Completed
          </v-btn>
        </div>

        <div v-else>
          <v-btn
            color="primary"
            @click="getWish"
          >
            Get Wish to Make True
          </v-btn>
        </div>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import { ref } from "vue";
import axios from "axios";

export default {
  name: "ElvesDashboard",
  setup() {
    const currentWish = ref(null);
    const loading = ref(false);

    // Function to get a wish to process
    const getWish = async () => {
      try {
        loading.value = true;
        const response = await axios.post("http://localhost:3000/api/beginProcessing");
        currentWish.value = response.data; // Assuming response contains { uuid, name, wish }
        console.log(JSON.stringify(currentWish.value));
        loading.value = false;
      } catch (error) {
        console.error("Error fetching wish:", error);
        alert("Error fetching wish. Maybe there are no wishes left to produce!");
        loading.value = false;
      }
    };

    // Function to mark the current wish as completed
    const markAsCompleted = async () => {
      if (!currentWish.value) return;

      try {
        await axios.patch(`http://localhost:3000/api/loadOnSleigh/${currentWish.value.wish.wish_id}`);
        currentWish.value = null; // Reset wish after marking it as completed
      } catch (error) {
        console.error("Error marking wish as completed:", error);
        alert("Error marking wish as completed.")
      }
    };

    return {
      currentWish,
      loading,
      getWish,
      markAsCompleted,
    };
  },
};
</script>

<style scoped>
/* Additional styles for alignment and spacing */
</style>
