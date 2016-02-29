package antcalc.models;

import java.util.HashMap;
import java.util.Map;

public class MainCalculation {
        private float N     = 24f;
        private float Nc    = 8f;
        private float KD    = 25.9f;
        private float eps   = 0.15f;
        private float m     = 0f;
        public static float dal   = 1f;
        private float Nal   = 180f / dal + 1f;
        private float Pi180 = 3.1415f / 180f;
        private float Psi   = 0f;
        private float Fi    = 0f;
        private float Ral   = 0f;
        private float DASigma   = 0f;

        private float[] Dg;
        private float[] Ra;
        private float[] Rna;
        private float[] SigmaNC;
        private float[] DeltaNC;

        private float firstLep = 0f;
        private float sixLep = 0f;
        private float maxOreol = 0f;
        private Map<Float, Integer> rLevel;

        private float DeltaFiMax;
        private boolean noErrors;
        private boolean AraspOne;

        public void calculateAll(boolean setNoErrors, boolean setArasp) {
            noErrors = setNoErrors;
            AraspOne = setArasp;
            int i       = 0;
            int dala    = (int) dal;
            float a     = 0f;
            float Max   = 0f;

            Dg          = new float[(int) Nal + 2];
            Ra          = new float[(int) Nal + 2];
            Rna         = new float[(int) Nal + 2];
            SigmaNC     = new float[(int) Nc + 1];
            DeltaNC     = new float[(int) Nc + 1];

            // DeltaNC && SigmaNC
            for (int iter = 1; iter <= Nc; iter++) {
                SigmaNC[iter] = MathHelper.getRandSigma();
                DeltaNC[iter] = MathHelper.getRandDelta();
            }

            // Цикл по углу al
            for (int al = 0; al <= 180; al += dala) { // тут шаг +dal
                i++;
                a = (float) al * Pi180;
                Ralfa(a);
                Dg[i] = al;
                Ra[i] = Ral;
            }

            // Поиск максимума
            Max = maxSearch();
            // Нормировка
            normalization(Max);
            // Расчиты первой таблицы
            firstTable(dala);
            // Расчеты второй таблицы
            resetSecondTable();
            secondTable(dala);
        }

        // Инициализация переменных второй таблицы
        private void resetSecondTable() {
            rLevel = new HashMap<>();
            rLevel.put(0.3f, 0);
            rLevel.put(0.25f, 0);
            rLevel.put(0.2f, 0);
            rLevel.put(0.18f, 0);
            rLevel.put(0.16f, 0);
            rLevel.put(0.14f, 0);
            rLevel.put(0.12f, 0);
            rLevel.put(0.1f, 0);
            rLevel.put(0.08f, 0);
            rLevel.put(0.06f, 0);
        }

        // Ранжирование
        private void secondTable(int dala) {
            for (int al = 0; al <= 180; al += dala) { // тут шаг +dal
                if (Rna[al] >= 0.3f)  rLevel.put(0.3f, rLevel.get(0.3f) + 1);
                if (Rna[al] >= 0.25f) rLevel.put(0.25f, rLevel.get(0.25f) + 1);
                if (Rna[al] >= 0.2f)  rLevel.put(0.2f, rLevel.get(0.2f) + 1);
                if (Rna[al] >= 0.18f) rLevel.put(0.18f, rLevel.get(0.18f) + 1);
                if (Rna[al] >= 0.16f) rLevel.put(0.16f, rLevel.get(0.18f) + 1);
                if (Rna[al] >= 0.14f) rLevel.put(0.14f, rLevel.get(0.14f) + 1);
                if (Rna[al] >= 0.25f) rLevel.put(0.12f, rLevel.get(0.12f) + 1);
                if (Rna[al] >= 0.12f) rLevel.put(0.1f, rLevel.get(0.1f) + 1);
                if (Rna[al] >= 0.08f) rLevel.put(0.08f, rLevel.get(0.08f) + 1);
                if (Rna[al] >= 0.06f) rLevel.put(0.06f, rLevel.get(0.06f) + 1);
            }
        }

        // Calculation for first table
        private void firstTable(int dala) {

            // Первый  спад
            float tempValue = Rna[10]; // TODO rebuild first calc
            int firstAl = 1;
            for (int al = 11; al <= 60; al += dala) { // тут шаг +dal
                if (tempValue > Rna[al]) {
                    firstAl = al;
                    tempValue = Rna[al];
                }
                else {
                    break;
                }
            }

            // 1й лепесток
            tempValue = 0f;
            for (int al = firstAl; al <= 60; al += dala) { // тут шаг +dal
                if (tempValue < Rna[al]) {
                    tempValue = Rna[al];
                }
                else {
                    break;
                }
            }
            firstLep = tempValue;

            // 60 градусов лепесток
            tempValue = 0f;
            for (int al = firstAl; al <= 60; al += dala) { // тут шаг +dal
                if (tempValue < Rna[al]) {
                    tempValue = Rna[al];
                }
                else {
                    //break;
                }
            }
            sixLep = tempValue;

            // Ореол макс значение
            maxOreol = 0f;
            for (int al = 60; al <= 180; al += dala) { // тут шаг +dal
                if (maxOreol < Rna[al]) {
                    maxOreol = Rna[al];
                }
            }


        }

        // Поиск максимума
        private float maxSearch() {
            float Max = 0f;
            for (int k = 1; k <= Nal; k++) {
                if (Ra[1] >= Ra[k + 1]) {
                    Max = Ra[1];
                } else {
                    Max = Ra[k + 1];
                }
            }
            return Max;
        }

        // Нормировка
        private void normalization(float Max) {
            for (int k = 1; k <= Nal; k++) {
                Rna[k] = Ra[k] / Max;
            }
        }

        // #1
        private float FiError(int i) {
            return (2f * SigmaNC[i] /*     * (float) i    */ - 1) * DeltaFiMax;
        }

        // #4
        private float FiSum(float FiErr, float Fi) {
            return Fi + FiErr;
        }

        // #5
        private float Arasp(float Psi, float PsiFirst) {
            if (AraspOne) {
                return 1f;
            }
            else {
                float part = (float) (Math.sin(Psi) / Math.sin(PsiFirst));
                return (float) (Math.abs(Math.cos(Psi)) * Math.exp(-m * (part * part)));
            }
        }

        // #6
        private float AError(int i) {
            return 1f + (2f * SigmaNC[i] - 1f) * DASigma;
        }

        //Формирование сумм, вычисление  R(alfa)
        private float Ralfa(float a) {
            float sumcos = 0f;
            float sumsin = 0f;
            float PsiFirst = 0f;
            float R1a;
            float eps1;
            float Ai;
            float Arasp;
            float FiErr;
            float FiSum;
            float AError;
            for (int i = 1; i <= Nc; i++) {
                FiErr = FiError(i);
                Psi  = (2f * i - Nc - 1f) * 3.1415f / N; // #2
                if (PsiFirst == 0f) PsiFirst = Psi;
                Fi   = (float) (KD * (Math.cos(Psi - a) - Math.cos(Psi)) / 2f); // #3
                FiSum = FiSum(FiErr, Fi); // # 4
                Arasp = Arasp(Psi, PsiFirst);
                AError = AError(i); // # 6
                eps1 = (float) ((1f - eps) * Math.cos(a - Psi)) / 2f; // parth of 7
                R1a  = eps1 + (float) Math.sqrt(eps1 * eps1 + eps); // #7
                if (noErrors) {
                    Ai = 1f;
                } else {
                    Ai = R1a * Arasp * AError; // #8
                }
                sumcos = (float) (sumcos + Ai * Math.cos(FiSum)); // Fi -> FiSum
                sumsin = (float) (sumsin + Ai * Math.sin(FiSum)); // Fi -> FiSum
            }
            Ral = (float) Math.sqrt(sumcos * sumcos + sumsin * sumsin);
            return Ral;
        }

        // Setters
        public void setN(float set_n) {
            this.N = set_n;
        }
        public void setNc(float nc) {
            this.Nc = nc;
        }
        public void setKD(float KD) {
            this.KD = KD;
        }
        public void setEps(float eps) {
            this.eps = eps;
        }
        public void setDeltaFiMax(float deltaFiMax) {
            DeltaFiMax = MathHelper.toRadians(deltaFiMax);
        }
        public void setM(float sM) {
            m = sM;
        }
        public void setDASigma(float DAS) {
            DASigma = (float) DAS;// * Math.PI / 180f);
        }

        // Getters
        public float[] getRa() {
            return Ra;
        }
        public float[] getRna() {
            return Rna;
        }
        public float[] getDg() {
            return Dg;
        }
        public float getmaxOreol(){return maxOreol;}
        public float getfirstLep(){return firstLep;}
        public float getSixLep() {
            return sixLep;
        }

    public Map<Float, Integer> getRLevel(){return rLevel;}
        public float[] getSigmaNC() {
            return SigmaNC;
        }
        public float[] getDeltaNC() {
            return DeltaNC;
        }
}
