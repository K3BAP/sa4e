import { Request, Response } from "express";
import client from "../../services/CassandraService";

/**
 * Fetches a wishlist and its related wishes for a given name.
 */
export const getWishlistByName = async (req: Request, res: Response): Promise<void> => {
  const { name } = req.query;

  if (!name) {
    res.status(400).json({ error: "'name' query parameter is required." });
    return;
  }

  try {
    // Query to fetch the wishlist by name
    const wishlistQuery = `
      SELECT wishlist_id, name
      FROM wishlists
      WHERE name = ?
      LIMIT 1
    `;

    const wishlistResult = await client.execute(wishlistQuery, [name], { prepare: true });

    if (wishlistResult.rowLength === 0) {
      res.status(404).json({ error: "Wishlist not found for the given name." });
      return;
    }

    const wishlist = wishlistResult.rows[0];
    const wishlistId = wishlist.wishlist_id;

    // Query to fetch wishes related to the wishlist ID
    const wishesQuery = `
      SELECT wish_id, wish, status
      FROM wishes
      WHERE wishlist_id = ?
    `;

    const wishesResult = await client.execute(wishesQuery, [wishlistId], { prepare: true });

    // Combine wishlist and wishes data into a single response
    res.status(200).json({
      wishlist: {
        id: wishlistId,
        name: wishlist.name,
        wishes: wishesResult.rows,
      },
    });
  } catch (err) {
    console.error("Error fetching wishlist and wishes:", err);
    res.status(500).json({ error: "Failed to fetch wishlist. Please try again later." });
  }
};
