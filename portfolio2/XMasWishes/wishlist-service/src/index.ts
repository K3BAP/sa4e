import express, { Application } from "express";
import bodyParser from "body-parser";
import routes from "./routes";
import dotenv from "dotenv";

// Load environment variables
dotenv.config();

const app: Application = express();
const port = process.env.PORT || 3000;

// Middleware
app.use(bodyParser.json());
app.use((req, res, next) => {
  res.header("Access-Control-Allow-Origin", "*"); // Allow all origins
  res.header(
    "Access-Control-Allow-Methods",
    "GET, POST, PATCH, PUT, DELETE, OPTIONS"
  ); // Allow specific HTTP methods
  res.header(
    "Access-Control-Allow-Headers",
    "Content-Type, Authorization"
  ); // Allow specific headers
  next();
})

// Routes
app.use("/api", routes);

// Start server
app.listen(port, () => {
  console.log(`Server is running on http://localhost:${port}`);
});
