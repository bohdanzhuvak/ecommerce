import {UpdateProfile} from '@/features/users/components/update-profile';
import {ContentLayout} from '@/shared/components/layouts';
import {DeleteUser} from "@/features/users/components/delete-user";
import {useAuth} from "@/shared/lib/auth";
import {BalanceHistory, DepositForm, useBalance} from '@/features/balance';
import {AddressForm, AddressList} from '@/features/delivery-addresses';

type EntryProps = {
  label: string;
  value: string;
};
const Entry = ({label, value}: EntryProps) => (
  <div className="py-4 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6 sm:py-5">
    <dt className="text-sm font-medium text-gray-500">{label}</dt>
    <dd className="mt-1 text-sm text-gray-900 sm:col-span-2 sm:mt-0">
      {value}
    </dd>
  </div>
);

export const ProfileRoute = () => {
  const user = useAuth().state.user;
  const { data: balanceData } = useBalance();

  if (!user) return null;

  return (
    <ContentLayout title="Profile">
      <div className="space-y-6">
        {/* User Information */}
        <div className="overflow-hidden bg-white shadow sm:rounded-lg">
          <div className="px-4 py-5 sm:px-6">
            <div className="flex justify-between">
              <h3 className="text-lg font-medium leading-6 text-gray-900">
                User Information
              </h3>
              <UpdateProfile/>
            </div>
            <p className="mt-1 max-w-2xl text-sm text-gray-500">
              Personal details of the user.
            </p>
          </div>
          <div className="border-t border-gray-200 px-4 py-5 sm:p-0">
            <dl className="sm:divide-y sm:divide-gray-200">
              <Entry label="Email" value={user.email}/>
              <Entry label="Username" value={user.username}/>
              <Entry label="Balance" value={`$${balanceData?.currentBalance?.toFixed(2) || '0.00'}`}/>
              <DeleteUser userEmail={user.email}/>
            </dl>
          </div>
        </div>

        {/* Balance Management */}
        <div className="overflow-hidden bg-white shadow sm:rounded-lg">
          <div className="px-4 py-5 sm:px-6">
            <div className="flex justify-between items-center">
              <div>
                <h3 className="text-lg font-medium leading-6 text-gray-900">
                  Balance Management
                </h3>
                <p className="mt-1 max-w-2xl text-sm text-gray-500">
                  Manage your account balance and transaction history.
                </p>
              </div>
              <DepositForm />
            </div>
          </div>
          <div className="border-t border-gray-200 px-4 py-5">
            <BalanceHistory />
          </div>
        </div>

        {/* Delivery Addresses */}
        <div className="overflow-hidden bg-white shadow sm:rounded-lg">
          <div className="px-4 py-5 sm:px-6">
            <div className="flex justify-between items-center">
              <div>
                <h3 className="text-lg font-medium leading-6 text-gray-900">
                  Delivery Addresses
                </h3>
                <p className="mt-1 max-w-2xl text-sm text-gray-500">
                  Manage your delivery addresses for orders.
                </p>
              </div>
              <AddressForm />
            </div>
          </div>
          <div className="border-t border-gray-200 px-4 py-5">
            <AddressList />
          </div>
        </div>
      </div>
    </ContentLayout>
  );
};
