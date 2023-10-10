import type {TurboModule} from 'react-native/Libraries/TurboModule/RCTExport';
import {TurboModuleRegistry} from 'react-native';

export interface Spec extends TurboModule {
  saveUnEncryptedData(data: string): Promise<{}>;
  saveEncryptedData(data: string): Promise<{}>;
  readUnEncryptedData(): Promise<{data: string}>;
  readEncryptedData(): Promise<{data: string}>;
  deleteData(): Promise<{}>;
  deleteAllData(): Promise<{}>;
}

export default TurboModuleRegistry.get<Spec>('RTNMyBlockStore') as Spec | null;
