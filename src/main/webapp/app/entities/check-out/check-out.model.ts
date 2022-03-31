import { IOrder } from 'app/entities/order/order.model';

export interface ICheckOut {
  id?: number;
  paymentType?: string | null;
  order?: IOrder | null;
}

export class CheckOut implements ICheckOut {
  constructor(public id?: number, public paymentType?: string | null, public order?: IOrder | null) {}
}

export function getCheckOutIdentifier(checkOut: ICheckOut): number | undefined {
  return checkOut.id;
}
