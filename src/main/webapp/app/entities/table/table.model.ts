import { IOrder } from 'app/entities/order/order.model';

export interface ITable {
  id?: number;
  number?: number | null;
  order?: IOrder | null;
}

export class Table implements ITable {
  constructor(public id?: number, public number?: number | null, public order?: IOrder | null) {}
}

export function getTableIdentifier(table: ITable): number | undefined {
  return table.id;
}
