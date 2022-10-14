import { rest } from 'msw'
import { cards } from '../dummy/cards'

export const cardHandlers = [
  rest.get("/api/cards", (req, res, ctx) => {
    return res(
      ctx.json({
        cards,
      })
    );
  }),
];