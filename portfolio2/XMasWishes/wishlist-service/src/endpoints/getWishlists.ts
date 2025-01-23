import client from "../services/CassandraService";
import { Request, Response } from 'express';

export default async function getWishlists(req: Request, res: Response) {
    try {
        const query = "SELECT * FROM wishlists";
        const result = await client.execute(query);
        res.status(200).json(result.rows);
      } catch (err) {
        console.error("Error retrieving data:", err);
        res.status(500).json({ error: "Failed to retrieve wishlist entries" });
      }
}