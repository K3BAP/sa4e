import { Router } from "express";
import getWishlists from "./endpoints/getWishlists";
import submitWishlist from "./endpoints/public/submitWishlist";
import ping from "./endpoints/ping";
import { beginProcessing } from "./endpoints/elves/beginProcessing";
import { getWishlistByName } from "./endpoints/santa/getWishlistByName";
import { getWishlistsInDelivery } from "./endpoints/santa/getWishlistsInDelivery";
import { setWishDelivered } from "./endpoints/santa/setWishDelivered";
import { loadOnSleigh } from "./endpoints/elves/loadOnSleigh";

const router = Router();

router.get("/ping", ping);

// Retrieve all wishlist entries
router.get("/getWishlists", getWishlists);

// Public Routes
router.post("/submitWishlist", submitWishlist);

// Routes for the Elves
router.post("/beginProcessing", beginProcessing);
router.patch("/loadOnSleigh/:wish_id", loadOnSleigh);

// Routes for Santa
router.get("/getWishlistByName", getWishlistByName);
router.get("/getWishlistsInDelivery", getWishlistsInDelivery);
router.patch("/setWishDelivered/:wish_id", setWishDelivered);

export default router;
