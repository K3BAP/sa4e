import { Request, Response } from 'express';

export default async function ping(req: Request, res: Response) {

    console.log("Ping message received")
    res.status(200).json("Pong!");

}