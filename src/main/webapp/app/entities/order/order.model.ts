import { IMenu } from 'app/entities/menu/menu.model';
import { ITable } from 'app/entities/table/table.model';
import { ICheckOut } from 'app/entities/check-out/check-out.model';

export interface IOrder {
  id?: number;
  orderNumber?: number | null;
  menus?: IMenu[] | null;
  table?: ITable | null;
  checkOut?: ICheckOut | null;
}

export class Order implements IOrder {
  constructor(
    public id?: number,
    public orderNumber?: number | null,
    public menus?: IMenu[] | null,
    public table?: ITable | null,
    public checkOut?: ICheckOut | null
  ) {}
}

export function getOrderIdentifier(order: IOrder): number | undefined {
  return order.id;
}
