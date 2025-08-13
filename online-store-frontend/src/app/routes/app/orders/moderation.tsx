import {ContentLayout} from '@/shared/components/layouts';
import {Authorization} from '@/shared/lib/auth/authorization';
import {ErrorBoundary} from 'react-error-boundary';
import {ROLES} from "@/shared/types/api.ts";
import {Orders} from '@/features/orders/components/orders';

export const ModerationOrdersRoute = () => {
  return (
    <Authorization allowedRoles={[ROLES.ADMIN]}>
      <ContentLayout title="Orders">
        <div className="mt-8">
          <ErrorBoundary fallback={<div>Failed to load orders.</div>}>
            <Orders clientEmail={''}/>
          </ErrorBoundary>
        </div>
      </ContentLayout>
    </Authorization>
  );
};
