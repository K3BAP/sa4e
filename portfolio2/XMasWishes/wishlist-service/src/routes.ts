import { Router } from "express";
import getWishlists from "./endpoints/getWishlists";
import submitWishlist from "./endpoints/submitWishlist";

const router = Router();

// Retrieve all wishlist entries
router.get("/getWishlists", getWishlists);

router.post("/submitWishlist", submitWishlist);

export default router;
