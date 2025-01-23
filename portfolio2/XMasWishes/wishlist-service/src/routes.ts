import { Router } from "express";
import getWishlists from "./endpoints/getWishlists";

const router = Router();

// Retrieve all wishlist entries
router.get("/getWishlists", getWishlists);

export default router;
