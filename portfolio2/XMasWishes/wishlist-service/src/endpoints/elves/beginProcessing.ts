import { Request, Response } from "express";
import client from "../../services/CassandraService";

/**
 * Retrieves a wish with the status "Formuliert", updates its status to "In Bearbeitung",
 * and returns the updated wish.
 */
export const beginProcessing = async (req: Request, res: Response): Promise<void> => {
  try {
    // Step 1: Find a wish with the status "Formuliert"
    const selectQuery = `
      SELECT wish_id, wishlist_id, wish, status
      FROM wishes
      WHERE status = 'Formuliert'
      LIMIT 1
      ALLOW FILTERING
    `;

    const result = await client.execute(selectQuery);

    if (result.rowLength === 0) {
      res.status(404).json({ error: "No wish found with the status 'Formuliert'." });
      return;
    }

    const wish = result.rows[0];

    // Step 2: Update the status of the wish to "In Bearbeitung"
    const updateQuery = `
      UPDATE wishes
      SET status = 'In Bearbeitung'
      WHERE wish_id = ?
    `;

    await client.execute(updateQuery, [wish.wish_id], { prepare: true });

    // Step 3: Return the updated wish
    res.status(200).json({
      message: "Wish status updated to 'In Bearbeitung'.",
      wish: {
        wish_id: wish.wish_id,
        wishlist_id: wish.wishlist_id,
        wish: wish.wish,
        status: "In Bearbeitung",
      },
    });
  } catch (err) {
    console.error("Error fetching and updating wish status:", err);
    res.status(500).json({ error: "Failed to fetch and update wish status. Please try again later." });
  }
};
