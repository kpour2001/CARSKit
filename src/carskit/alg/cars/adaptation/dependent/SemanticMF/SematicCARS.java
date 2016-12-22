package carskit.alg.cars.adaptation.dependent.SemanticMF;

import carskit.alg.cars.adaptation.dependent.CAMF;
import carskit.data.structure.SparseMatrix;
import librec.data.DenseMatrix;

import java.util.List;

/**
 * Created by Mohammad Pourzaferani on 12/22/16.
 */
public class SematicCARS extends CAMF {
    public SematicCARS(SparseMatrix trainMatrix, SparseMatrix testMatrix, int fold) {
        super(trainMatrix, testMatrix, fold);
        this.algoName = "SematicCARS";
    }

    @Override
    protected List<Integer> getConditions(int ctx) {
        return super.getConditions(ctx);
    }

    @Override
    protected double predict(int u, int j, int c) throws Exception {
        double pred=globalMean + DenseMatrix.rowMult(P, u, Q, j);
        for(int cond:getConditions(c)){
            pred+=icBias.get(j,cond)+ucBias.get(u,cond);
        }
        return pred;
    }
}
