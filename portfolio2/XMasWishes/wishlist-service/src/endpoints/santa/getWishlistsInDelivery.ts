import { Request, Response } from "express";
import client from "../../services/CassandraService";

/**
 * Fetches a list of wishlists where all associated wishes have a status of "in delivery".
 * The number of results is determined by the "limit" query parameter.
 */
export const getWishlistsInDelivery = async (req: Request, res: Response): Promise<void> => {
  let limit = parseInt(req.query.limit as string, 10);

  if (isNaN(limit) || limit <= 0) {
    limit = 10;
  }

  try {
    // Query to fetch wishlists with all wishes in "in delivery" status
    const query = `
      SELECT wishlist_id, name
      FROM wishlists
      WHERE wishlist_id IN (
        SELECT wishlist_id
        FROM wishes
        WHERE status = 'In Zustellung'
        GROUP BY wishlist_id
        HAVING COUNT(*) = COUNT(IF(status = 'in delivery', 1, NULL))
      )
      LIMIT ?
    `;

    // Execute the query
    const result = await client.execute(query, [limit], { prepare: true });

    res.status(200).json(result.rows);
  } catch (err) {
    console.error("Error fetching wishlists:", err);
    res.status(500).json({ error: "Failed to fetch wishlists. Please try again later." });
  }
};
