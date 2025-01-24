import { Request, Response } from "express";
import client from "../../services/CassandraService";

/**
 * Updates the status of a specific wish (given by its UUID) to "completed".
 */
export const setWishDelivered = async (req: Request, res: Response): Promise<void> => {
  const { wish_id } = req.params;

  if (!wish_id) {
    res.status(400).json({ error: "'wish_id' parameter is required." });
    return;
  }

  try {
    // Query to update the status of the wish
    const query = `
      UPDATE wishes
      SET status = 'Zugestellt'
      WHERE wish_id = ?
    `;

    await client.execute(query, [wish_id], { prepare: true });

    res.status(200).json({ message: `Wish with ID ${wish_id} has been marked as completed.` });
  } catch (err) {
    console.error("Error updating wish status:", err);
    res.status(500).json({ error: "Failed to update wish status. Please try again later." });
  }
};
