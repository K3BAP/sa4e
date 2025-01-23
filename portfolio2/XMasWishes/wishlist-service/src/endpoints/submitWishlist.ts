import {Request, Response} from 'express';
import { v4 as uuidv4 } from "uuid";
import client from '../services/CassandraService';

export default async function submitWishlist(req: Request, res: Response) {
    const {name, wishes} = req.body;

    // Verify request
    if (!name || !Array.isArray(wishes) || wishes.length === 0) {
        res.status(400).json({ error: "Invalid request. 'name' and 'wishes' are required." });
        return;
    }

    // Prepare Cassandra queries
    const insertWishlistQuery = `
    INSERT INTO wishlists (wishlist_id, name)
    VALUES (?, ?)
    `;

    const insertWishQuery = `
    INSERT INTO wishes (wish_id, wishlist_id, wish, status)
    VALUES (?, ?, ?, ?)
    `;
    
    // create wishlist id
    const wishlistId = uuidv4();

    try {
        // Insert the wishlist
        await client.execute(insertWishlistQuery, [wishlistId, name], { prepare: true });
    
        // Insert each wish
        for (const wish of wishes) {
          const wishId = uuidv4();
          const status = "Formuliert";
          await client.execute(insertWishQuery, [wishId, wishlistId, wish, status], { prepare: true });
        }
    
        res.status(201).json({ message: "Wishlist and wishes submitted successfully!" });
      } catch (err) {
        console.error("Error submitting wishlist and wishes:", err);
        res.status(500).json({ error: "Failed to submit wishlist. Please try again later." });
      }
}