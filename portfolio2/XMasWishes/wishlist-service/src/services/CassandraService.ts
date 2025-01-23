import { Client } from "cassandra-driver";
import dotenv from "dotenv";

// Load environment variables
dotenv.config();

const client = new Client({
  contactPoints: [process.env.CASSANDRA_CONTACT_POINTS || "127.0.0.1"],
  localDataCenter: process.env.CASSANDRA_DATA_CENTER || 'datacenter1',
  keyspace: process.env.CASSANDRA_KEYSPACE || "xmaswishes",
});

// Test the connection when the service starts
(async () => {
  try {
    await client.connect();
    console.log("Connected to Cassandra!");
  } catch (err) {
    console.error("Failed to connect to Cassandra:", err);
    process.exit(1);
  }
})();

export default client;
