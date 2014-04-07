namespace Com.Ericmas001.Game.Poker.GUI.Game
{
    partial class TableForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(TableForm));
            this.flowLayoutPanel1 = new System.Windows.Forms.FlowLayoutPanel();
            this.btnFold = new VIBlend.WinForms.Controls.vButton();
            this.btnCall = new VIBlend.WinForms.Controls.vButton();
            this.btnRaise = new VIBlend.WinForms.Controls.vButton();
            this.nudRaise = new System.Windows.Forms.NumericUpDown();
            this.btnSitIn = new VIBlend.WinForms.Controls.vButton();
            this.flowLayoutPanel1.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).BeginInit();
            this.SuspendLayout();
            // 
            // flowLayoutPanel1
            // 
            this.flowLayoutPanel1.Controls.Add(this.btnFold);
            this.flowLayoutPanel1.Controls.Add(this.btnCall);
            this.flowLayoutPanel1.Controls.Add(this.btnRaise);
            this.flowLayoutPanel1.Controls.Add(this.nudRaise);
            this.flowLayoutPanel1.Controls.Add(this.btnSitIn);
            this.flowLayoutPanel1.FlowDirection = System.Windows.Forms.FlowDirection.TopDown;
            this.flowLayoutPanel1.Location = new System.Drawing.Point(874, 52);
            this.flowLayoutPanel1.Name = "flowLayoutPanel1";
            this.flowLayoutPanel1.Size = new System.Drawing.Size(146, 501);
            this.flowLayoutPanel1.TabIndex = 3;
            // 
            // btnFold
            // 
            this.btnFold.AllowAnimations = true;
            this.btnFold.BackColor = System.Drawing.Color.Transparent;
            this.btnFold.Enabled = false;
            this.btnFold.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnFold.Image = global::Com.Ericmas001.Game.Poker.GUI.Properties.Resources.leave;
            this.btnFold.Location = new System.Drawing.Point(5, 5);
            this.btnFold.Margin = new System.Windows.Forms.Padding(5);
            this.btnFold.Name = "btnFold";
            this.btnFold.RoundedCornersMask = ((byte)(15));
            this.btnFold.Size = new System.Drawing.Size(135, 23);
            this.btnFold.TabIndex = 0;
            this.btnFold.Text = "FOLD";
            this.btnFold.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnFold.UseVisualStyleBackColor = true;
            this.btnFold.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROORANGE;
            this.btnFold.Click += new System.EventHandler(this.btnFold_Click);
            // 
            // btnCall
            // 
            this.btnCall.AllowAnimations = true;
            this.btnCall.BackColor = System.Drawing.Color.Transparent;
            this.btnCall.Enabled = false;
            this.btnCall.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnCall.Image = global::Com.Ericmas001.Game.Poker.GUI.Properties.Resources.cards_mini;
            this.btnCall.Location = new System.Drawing.Point(5, 38);
            this.btnCall.Margin = new System.Windows.Forms.Padding(5);
            this.btnCall.Name = "btnCall";
            this.btnCall.RoundedCornersMask = ((byte)(15));
            this.btnCall.Size = new System.Drawing.Size(135, 23);
            this.btnCall.TabIndex = 1;
            this.btnCall.Text = "CALL";
            this.btnCall.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnCall.UseVisualStyleBackColor = true;
            this.btnCall.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROGREEN;
            this.btnCall.Click += new System.EventHandler(this.btnCall_Click);
            // 
            // btnRaise
            // 
            this.btnRaise.AllowAnimations = true;
            this.btnRaise.BackColor = System.Drawing.Color.Transparent;
            this.btnRaise.Enabled = false;
            this.btnRaise.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnRaise.Image = global::Com.Ericmas001.Game.Poker.GUI.Properties.Resources.money;
            this.btnRaise.Location = new System.Drawing.Point(5, 71);
            this.btnRaise.Margin = new System.Windows.Forms.Padding(5);
            this.btnRaise.Name = "btnRaise";
            this.btnRaise.RoundedCornersMask = ((byte)(15));
            this.btnRaise.Size = new System.Drawing.Size(135, 23);
            this.btnRaise.TabIndex = 2;
            this.btnRaise.Text = "RAISE";
            this.btnRaise.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnRaise.UseVisualStyleBackColor = true;
            this.btnRaise.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROBLUE;
            this.btnRaise.Click += new System.EventHandler(this.btnRaise_Click);
            // 
            // nudRaise
            // 
            this.nudRaise.Enabled = false;
            this.nudRaise.Location = new System.Drawing.Point(5, 104);
            this.nudRaise.Margin = new System.Windows.Forms.Padding(5);
            this.nudRaise.Name = "nudRaise";
            this.nudRaise.Size = new System.Drawing.Size(135, 20);
            this.nudRaise.TabIndex = 3;
            // 
            // btnSitIn
            // 
            this.btnSitIn.AllowAnimations = true;
            this.btnSitIn.BackColor = System.Drawing.Color.Transparent;
            this.btnSitIn.Font = new System.Drawing.Font("Microsoft Sans Serif", 8.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnSitIn.Image = global::Com.Ericmas001.Game.Poker.GUI.Properties.Resources.sit_in;
            this.btnSitIn.Location = new System.Drawing.Point(5, 134);
            this.btnSitIn.Margin = new System.Windows.Forms.Padding(5);
            this.btnSitIn.Name = "btnSitIn";
            this.btnSitIn.RoundedCornersMask = ((byte)(15));
            this.btnSitIn.Size = new System.Drawing.Size(135, 23);
            this.btnSitIn.TabIndex = 4;
            this.btnSitIn.Text = "SIT IN";
            this.btnSitIn.TextImageRelation = System.Windows.Forms.TextImageRelation.TextBeforeImage;
            this.btnSitIn.UseVisualStyleBackColor = true;
            this.btnSitIn.VIBlendTheme = VIBlend.Utilities.VIBLEND_THEME.METROGREEN;
            this.btnSitIn.Click += new System.EventHandler(this.btnSitIn_Click);
            // 
            // TableForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1018, 687);
            this.Controls.Add(this.flowLayoutPanel1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.Name = "TableForm";
            this.Text = "C# Poker Table";
            this.Controls.SetChildIndex(this.flowLayoutPanel1, 0);
            this.flowLayoutPanel1.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.nudRaise)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.FlowLayoutPanel flowLayoutPanel1;
        private VIBlend.WinForms.Controls.vButton btnFold;
        private VIBlend.WinForms.Controls.vButton btnCall;
        private VIBlend.WinForms.Controls.vButton btnRaise;
        private System.Windows.Forms.NumericUpDown nudRaise;
        private VIBlend.WinForms.Controls.vButton btnSitIn;


    }
}