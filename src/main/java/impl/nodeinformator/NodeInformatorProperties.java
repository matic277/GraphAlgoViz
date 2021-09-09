package impl.nodeinformator;

public class NodeInformatorProperties {
    
    // TODO this should be a strategy (interface)
    // and two implementations: totalInformedImpl
    //                          percentageInformedImpl
    
    // one or the other
    private Integer totalNodesToInform;
    private Double informedProbability;
    
    public NodeInformatorProperties() {}
    
    public Integer getTotalNodesToInform() {
        return totalNodesToInform;
    }
    
    public void setTotalNodesToInform(Integer totalNodesToInform) {
        this.totalNodesToInform = totalNodesToInform;
    }
    
    public Double getInformedProbability() {
        return informedProbability;
    }
    
    public void setInformedProbability(Double percentageToInform) {
        this.informedProbability = percentageToInform;
    }
}
