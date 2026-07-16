class ipadd {
    public String defangIPaddr(String address) {
        return address.replace(".", "[.]");
    }
}