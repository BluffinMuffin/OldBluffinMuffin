namespace BluffinPokerGUI.Lobby
{
    partial class AddTableForm
    {
        /// <summary>
        /// Variable nécessaire au concepteur.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Nettoyage des ressources utilisées.
        /// </summary>
        /// <param name="disposing">true si les ressources managées doivent être supprimées ; sinon, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Code généré par le Concepteur Windows Form

        /// <summary>
        /// Méthode requise pour la prise en charge du concepteur - ne modifiez pas
        /// le contenu de cette méthode avec l'éditeur de code.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(AddTableForm));
            this.tabControl1 = new System.Windows.Forms.TabControl();
            this.tabReal = new System.Windows.Forms.TabPage();
            this.btnAddCareer = new VIBlend.WinForms.Controls.vButton();
            this.atcReal = new BluffinPokerGUI.Lobby.AddTableControl();
            this.tabTraining = new System.Windows.Forms.TabPage();
            this.nudStartingAmnt = new System.Windows.Forms.NumericUpDown();
            this.lblBigBlindAmnt = new System.Windows.Forms.Label();
            this.btnAddTraining = new VIBlend.WinForms.Controls.vButton();
            this.atcTraining = new BluffinPokerGUI.Lobby.AddTableControl();
            this.tabControl1.SuspendLayout();
            this.tabReal.SuspendLayout();
            this.tabTraining.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudStartingAmnt)).BeginInit();
            this.SuspendLayout();
            // 
            // tabControl1
            // 
            this.tabControl1.Controls.Add(this.tabReal);
            this.tabControl1.Controls.Add(this.tabTraining);
            this.tabControl1.Location = new System.Drawing.Point(12, 12);
            this.tabControl1.Name = "tabControl1";
            this.tabControl1.SelectedIndex = 0;
            this.tabControl1.Size = new System.Drawing.Size(317, 277);
            this.tabControl1.TabIndex = 13;
            // 
            // tabReal
            // 
            this.tabReal.Controls.Add(this.btnAddCareer);
            this.tabReal.Controls.Add(this.atcReal);
            this.tabReal.Location = new System.Drawing.Point(4, 22);
            this.tabReal.Name = "tabReal";
            this.tabReal.Padding = new System.Windows.Forms.Padding(3);
            this.tabReal.Size = new System.Drawing.Size(309, 251);
            this.tabReal.TabIndex = 0;
            this.tabReal.Text = "Career";
            this.tabReal.UseVisualStyleBackColor = true;
            // 
            // btnAddCareer
            // 
            this.btnAddCareer.AllowAnimations = true;
            this.btnAddCareer.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnAddCareer.BackColor = System.Drawing.Color.Transparent;
            this.btnAddCareer.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAddCareer.Image = global::BluffinPokerGUI.Properties.Resources.cards;
            this.btnAddCareer.Location = new System.Drawing.Point(72, 192);
            this.btnAddCareer.Name = "btnAddCareer";
            this.btnAddCareer.RoundedCornersMask = ((byte)(15));
            this.btnAddCareer.Size = new System.Drawing.Size(167, 53);
            this.btnAddCareer.TabIndex = 15;
            this.btnAddCareer.Text = "Create Table";
            this.btnAddCareer.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnAddCareer.UseVisualStyleBackColor = true;
            this.btnAddCareer.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnAddCareer.Click += new System.EventHandler(this.btnAddReal_Click);
            // 
            // atcReal
            // 
            this.atcReal.Location = new System.Drawing.Point(6, 6);
            this.atcReal.Name = "atcReal";
            this.atcReal.Size = new System.Drawing.Size(296, 165);
            this.atcReal.TabIndex = 14;
            // 
            // tabTraining
            // 
            this.tabTraining.Controls.Add(this.nudStartingAmnt);
            this.tabTraining.Controls.Add(this.lblBigBlindAmnt);
            this.tabTraining.Controls.Add(this.btnAddTraining);
            this.tabTraining.Controls.Add(this.atcTraining);
            this.tabTraining.Location = new System.Drawing.Point(4, 22);
            this.tabTraining.Name = "tabTraining";
            this.tabTraining.Padding = new System.Windows.Forms.Padding(3);
            this.tabTraining.Size = new System.Drawing.Size(309, 251);
            this.tabTraining.TabIndex = 1;
            this.tabTraining.Text = "Training";
            this.tabTraining.UseVisualStyleBackColor = true;
            // 
            // nudStartingAmnt
            // 
            this.nudStartingAmnt.Increment = new decimal(new int[] {
            100,
            0,
            0,
            0});
            this.nudStartingAmnt.Location = new System.Drawing.Point(99, 164);
            this.nudStartingAmnt.Maximum = new decimal(new int[] {
            1000000,
            0,
            0,
            0});
            this.nudStartingAmnt.Minimum = new decimal(new int[] {
            500,
            0,
            0,
            0});
            this.nudStartingAmnt.Name = "nudStartingAmnt";
            this.nudStartingAmnt.Size = new System.Drawing.Size(84, 20);
            this.nudStartingAmnt.TabIndex = 16;
            this.nudStartingAmnt.Value = new decimal(new int[] {
            1500,
            0,
            0,
            0});
            // 
            // lblBigBlindAmnt
            // 
            this.lblBigBlindAmnt.AutoSize = true;
            this.lblBigBlindAmnt.Location = new System.Drawing.Point(8, 166);
            this.lblBigBlindAmnt.Name = "lblBigBlindAmnt";
            this.lblBigBlindAmnt.Size = new System.Drawing.Size(85, 13);
            this.lblBigBlindAmnt.TabIndex = 17;
            this.lblBigBlindAmnt.Text = "Starting Amount:";
            // 
            // btnAddTraining
            // 
            this.btnAddTraining.AllowAnimations = true;
            this.btnAddTraining.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnAddTraining.BackColor = System.Drawing.Color.Transparent;
            this.btnAddTraining.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnAddTraining.Image = global::BluffinPokerGUI.Properties.Resources.learn;
            this.btnAddTraining.Location = new System.Drawing.Point(76, 192);
            this.btnAddTraining.Name = "btnAddTraining";
            this.btnAddTraining.RoundedCornersMask = ((byte)(15));
            this.btnAddTraining.Size = new System.Drawing.Size(167, 53);
            this.btnAddTraining.TabIndex = 13;
            this.btnAddTraining.Text = "Create Table";
            this.btnAddTraining.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnAddTraining.UseVisualStyleBackColor = true;
            this.btnAddTraining.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnAddTraining.Click += new System.EventHandler(this.btnAddTraining_Click);
            // 
            // atcTraining
            // 
            this.atcTraining.Location = new System.Drawing.Point(6, 6);
            this.atcTraining.Name = "atcTraining";
            this.atcTraining.Size = new System.Drawing.Size(296, 165);
            this.atcTraining.TabIndex = 12;
            // 
            // AddTableForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(336, 298);
            this.Controls.Add(this.tabControl1);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "AddTableForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Add Table";
            this.tabControl1.ResumeLayout(false);
            this.tabReal.ResumeLayout(false);
            this.tabTraining.ResumeLayout(false);
            this.tabTraining.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudStartingAmnt)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private AddTableControl atcTraining;
        private System.Windows.Forms.TabControl tabControl1;
        private System.Windows.Forms.TabPage tabReal;
        private System.Windows.Forms.TabPage tabTraining;
        private VIBlend.WinForms.Controls.vButton btnAddTraining;
        private AddTableControl atcReal;
        private System.Windows.Forms.NumericUpDown nudStartingAmnt;
        private System.Windows.Forms.Label lblBigBlindAmnt;
        private VIBlend.WinForms.Controls.vButton btnAddCareer;
    }
}

