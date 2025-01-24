import { Router } from "express";
import getWishlists from "./endpoints/getWishlists";
import submitWishlist from "./endpoints/submitWishlist";
import ping from "./endpoints/ping";

const router = Router();

router.get("/ping", ping);

// Retrieve all wishlist entries
router.get("/getWishlists", getWishlists);
router.post("/submitWishlist", submitWishlist);

export default router;
