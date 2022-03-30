package by.itacademy.zuevvlad.cardpaymentproject.dao.cryptographer;

public interface Cryptographer<TypeOfNeededEncryptingData, TypeOfNeedDecryptingData>
{
    public abstract TypeOfNeedDecryptingData encrypt(final TypeOfNeededEncryptingData encryptedData);
    public abstract TypeOfNeededEncryptingData decrypt(final TypeOfNeedDecryptingData decryptedData);
}
