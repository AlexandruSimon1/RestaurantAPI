import { IOrder } from 'app/entities/order/order.model';
import { CategoryType } from 'app/entities/enumerations/category-type.model';

export interface IMenu {
  id?: number;
  name?: string | null;
  description?: string | null;
  price?: number | null;
  categoryType?: CategoryType | null;
  order?: IOrder | null;
}

export class Menu implements IMenu {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public price?: number | null,
    public categoryType?: CategoryType | null,
    public order?: IOrder | null
  ) {}
}

export function getMenuIdentifier(menu: IMenu): number | undefined {
  return menu.id;
}
