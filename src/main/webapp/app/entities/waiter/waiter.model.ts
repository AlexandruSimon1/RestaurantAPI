import dayjs from 'dayjs/esm';

export interface IWaiter {
  id?: number;
  firstName?: string | null;
  lastName?: string | null;
  dateOfBirth?: dayjs.Dayjs | null;
  address?: string | null;
  phoneNumber?: number | null;
  email?: string | null;
}

export class Waiter implements IWaiter {
  constructor(
    public id?: number,
    public firstName?: string | null,
    public lastName?: string | null,
    public dateOfBirth?: dayjs.Dayjs | null,
    public address?: string | null,
    public phoneNumber?: number | null,
    public email?: string | null
  ) {}
}

export function getWaiterIdentifier(waiter: IWaiter): number | undefined {
  return waiter.id;
}
